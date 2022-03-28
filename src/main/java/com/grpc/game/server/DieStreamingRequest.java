package com.grpc.game.server;

import com.grpc.game.Die;
import com.grpc.game.GameState;
import com.grpc.game.Player;

import io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;
import io.grpc.stub.StreamObserver;

public class DieStreamingRequest implements StreamObserver<Die>{
	
	private Player client;
	private Player server;
	
	public DieStreamingRequest(Player client, Player server, StreamObserver<GameState> gameStateStreamObserver) {
		super();
		this.client = client;
		this.server = server;
		this.gameStateStreamObserver = gameStateStreamObserver;
	}

	private StreamObserver<GameState> gameStateStreamObserver;

	@Override
	public void onNext(Die value) {
		int dieValue = value.getValue();
		this.client = this.getNewPlayerPosition(client, dieValue);
		
		if (this.client.getPosition() != 100) {
			int serverDieValue = ThreadLocalRandom.current().nextInt(1, 7);
			this.server = this.getNewPlayerPosition(server, serverDieValue);
		}
		
		this.gameStateStreamObserver.onNext(this.getGameState());
		
	}

	@Override
	public void onError(Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCompleted() {
		this.gameStateStreamObserver.onCompleted();
		
	}
	
	private Player getNewPlayerPosition(Player player, int dieValue) {
		int position = player.getPosition() + dieValue;
		position = SnakesAndLaddersMap.getPosition(position);
		
		if (position <= 100) {
			player = player.toBuilder()
					.setPosition(position)
					.build();
		}
		
		return player;
	}
	
	private GameState getGameState() {
		return GameState.newBuilder()
				.addPlayer(this.client)
				.addPlayer(this.server)
				.build();
	}

}
