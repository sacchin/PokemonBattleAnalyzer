package com.gmail.sacchin13.pokemonbattleanalyzer.util

object StatusCalculator {
    fun calcHitPoint(h: Int, effortValues: Int): Int {
        return (h * 2 + 31 + effortValues / 4) / 2 + 10 + 50
    }

    fun calcAttack(a: Int, effortValues: Int): Int {
        return (a * 2 + 31 + effortValues / 4) / 2 + 5
    }

    fun calcBlock(b: Int, effortValues: Int): Int {
        return (b * 2 + 31 + effortValues / 4) / 2 + 5
    }

    fun calcConcentration(c: Int, effortValues: Int): Int {
        return (c * 2 + 31 + effortValues / 4) / 2 + 5
    }

    fun calcDefence(d: Int, effortValues: Int): Int {
        return (d * 2 + 31 + effortValues / 4) / 2 + 5
    }

    fun calcSpeed(s: Int, effortValues: Int): Int {
        return (s * 2 + 31 + effortValues / 4) / 2 + 5
    }
}
