// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: game-service.proto

package com.grpc.game;

public interface GameStateOrBuilder extends
    // @@protoc_insertion_point(interface_extends:GameState)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .Player player = 1;</code>
   */
  java.util.List<com.grpc.game.Player> 
      getPlayerList();
  /**
   * <code>repeated .Player player = 1;</code>
   */
  com.grpc.game.Player getPlayer(int index);
  /**
   * <code>repeated .Player player = 1;</code>
   */
  int getPlayerCount();
  /**
   * <code>repeated .Player player = 1;</code>
   */
  java.util.List<? extends com.grpc.game.PlayerOrBuilder> 
      getPlayerOrBuilderList();
  /**
   * <code>repeated .Player player = 1;</code>
   */
  com.grpc.game.PlayerOrBuilder getPlayerOrBuilder(
      int index);
}
