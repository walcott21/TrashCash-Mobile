package com.example.drawernav.models

data class ScoreboardEntry(
    val yourPoints: Int,
    val positionGlobalRank: Int,
    val globalRanking: List<Rank>,
)

data class Rank(
    val name:String,
    val points:Int,
    val position:Int
)
