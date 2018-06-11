package com.zhtkj.jt808.server;

import java.awt.GridLayout;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;

/**
 * ClassName: ServerApplication 
 * @Description: 主程序及引导程序
 */
public class ServerApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);
	
	public static AbstractApplicationContext appCtx;
	
	private volatile boolean isRunning = false;

	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	private int port;

	public ServerApplication(int port) {
		this.port = port;
	}

	private void bind() throws Exception {
		this.bossGroup = new NioEventLoopGroup();
		this.workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class) 
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));
							//1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, true, Unpooled.copiedBuffer(new byte[] { 0x7e }),
									Unpooled.copiedBuffer(new byte[] { 0x7e, 0x7e })));
							//添加业务处理handler
							ch.pipeline().addLast(new ServerHandler());
						}
					}).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			LOGGER.info(this.getName() + "启动完毕, 端口={}", this.port);
			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            this.workerGroup.shutdownGracefully();
            this.bossGroup.shutdownGracefully();
		}
	}

	public synchronized void startServer() {
		if (this.isRunning) {
			throw new IllegalStateException(this.getName() + "已启动，不能重复启动");
		}
		this.isRunning = true;
		
		new Thread(() -> {
			try {
				this.bind();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, this.getName()).start();
	}

	public synchronized void stopServer() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName() + "未启动");
		}
		this.isRunning = false;

		try {
			Future<?> future = this.workerGroup.shutdownGracefully().await();
			if (!future.isSuccess()) {
			}
			future = this.bossGroup.shutdownGracefully().await();
			if (!future.isSuccess()) {
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String getName() {
		return "终端通讯服务端";
	}

	public static void main(String[] args) throws Exception {
		//初始化spring容器
        appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //初始化netty
		ServerApplication server = new ServerApplication(6666);
		server.startServer();
		
		//初始化窗口，这个窗口只是用来展示服务端是否启动，没有其他作用
		JFrame frame = new JFrame();  
        JPanel panel = new JPanel();  
        panel.setLayout(new GridLayout());  
        frame.add(panel);  
        frame.setTitle("终端通讯服务端");
        frame.setSize(618, 381);  
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}