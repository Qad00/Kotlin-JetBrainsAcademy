package connectfour

var number0fGames = 1
var col = 7
var row = 6
var firstPlayer = ""
var secondPlayer = ""

fun initGameD() {
    println("First player's name:")
    firstPlayer = readln()
    println("Second player's name:")
    secondPlayer = readln()
    while (true) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        var temp = ""
        try {
            temp = readln()
            val (rowT, colT) = temp.split("x", "X").map { it.trim().toInt() }
            row = rowT
            col = colT
        } catch (e: Exception) {
            if (temp == "") break
            else {
                println("Invalid input")
                continue
            }
        }
        if (row < 5 || row > 9) println("Board rows should be from 5 to 9")
        else if (col < 5 || col > 9) println("Board columns should be from 5 to 9")
        else break
    }

    while (true) {
        println("Do you want to play single or multiple games?")
        println("For a single game, input 1 or press Enter")
        println("Input a number of games:")
        val temp = readln()
        if (temp == "") break
        else if (temp.matches(Regex("\\d+")) && temp.toInt() > 0) {
            number0fGames = temp.toInt()
            break
        } else println("Invalid input")
    }
}

fun main() {
    println("Connect four")
    initGameD()
    val fourConnect = FourConnect(firstPlayer, secondPlayer, row, col, number0fGames)
    fourConnect.printData()
    fourConnect.printBoard()
    while (!fourConnect.exit)
        fourConnect.startGame()
    println("Game over!")
}