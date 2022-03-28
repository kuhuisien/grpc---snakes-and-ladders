package com.grpc.game.client;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.Uninterruptibles;
import com.grpc.game.Die;
import com.grpc.game.GameState;
import com.grpc.game.Player;

import io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;
import io.grpc.stub.StreamObserver;

public class GameStateStreamingResponse implements StreamObserver<GameState>{
	
	private CountDownLatch latch;
	private StreamObserver<Die> dieStreamObserver;

	public GameStateStreamingResponse(CountDownLatch latch) {
		super();
		this.latch = latch;
	}

	@Override
	public void onNext(GameState value) {
		List<Player> list = value.getPlayerList();
		list.forEach(p -> System.out.println(p.getName() + ": " + p.getPosition()));
		
		boolean isGameOver = list.stream().anyMatch(p -> p.getPosition() == 100);
		
		if (isGameOver) {
			System.out.println("Game over");
			this.dieStreamObserver.onCompleted();
		} else {
			Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
			this.roll();
		}
		
		System.out.println(".........................");
	}

	@Override
	public void onError(Throwable t) {
		this.latch.countDown();
	}

	@Override
	public void onCompleted() {
		this.latch.countDown();
	}
	
	public void setDieStreamObserver(StreamObserver<Die> streamObserver) {
		this.dieStreamObserver = streamObserver;
	}
	
	public void roll() {
		int dieValue = ThreadLocalRandom.current().nextInt(1, 7);
		Die die = Die.newBuilder().setValue(dieValue).build();
		this.dieStreamObserver.onNext(die);
	}

}
