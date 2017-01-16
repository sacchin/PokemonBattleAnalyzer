package com.gmail.sacchin13.pokemonbattleanalyzer.entity

data class SaveRequest (
        val requests: List<RequestEntity>
){

    data class RequestEntity (
            val updateCells: UpdateCellsEntity
    ){}

    data class UpdateCellsEntity (
            val start: StartEntity,
            val rows: List<RowEntity>
    ){}

    data class StartEntity (
            val sheetId: Int,
            val rowIndex: Int,
            val columnIndex: Int
    ){}

    data class RowEntity (
            val values: List<ValueEntity>
    ){}

    data class ValueEntity (
            val userEnteredValue: UserEnteredValueEntity
    ){}

    data class UserEnteredValueEntity (
            val stringValue: String
    ){}
}