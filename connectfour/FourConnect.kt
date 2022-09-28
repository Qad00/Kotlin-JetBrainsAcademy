package connectfour

class FourConnect(private var firstPlayer: String, private var secondPlayer: String,
                  private var row: Int, private var col: Int, private var numberOfGames: Int) {
    private var countMove = 0
    private var gameNumber = 1
    private var board = Array(row + 1) { Array(col + 1) { " " } }
    var exit = false
    private var scoreFP = 0
    private var scoreSP = 0

    fun printData() {
        println("${this.firstPlayer} VS ${this.secondPlayer}")
        println("${this.row} X ${this.col} board")
        if (this.numberOfGames == 1) println("Single game")
        else {
            println("Total ${this.numberOfGames} games")
            println("Game #${this.gameNumber}")
        }
    }

    fun printBoard() {
        for (i in 1..col)
            if (i != col) print(" $i") else println(" $i")

        for (i in 0..row) {
            for (j in 0..col) {
                if (i == row) {
                    when (j) {
                        0 -> print("╚")
                        col -> print("╝")
                        else -> if (j != col - 1) print("═╩") else print("═╩═")
                    }
                } else print("║" + board[i][j])
            }
            println()
        }
    }

    private fun checkColumn(reg: String): Boolean {
        for (i in 0 until col) {
            var t = ""
            for (j in 0 until row) {
                t += board[j][i]
            }
            if (t.contains(reg))
                return true
        }
        return false
    }

    private fun checkDiagonal(reg: String): Boolean {
        val tempArR = Array(row + col) {""}
        val tempArL = Array(row + col) {""}
        for (i in 0 until row) {
            for (j in 0 until col) {
                if (j - i >= 0) tempArR[j - i] += board[i][j]
                if (i - j > 0) tempArR[col + i - j] += board[i][j]
                tempArL[i + j] += board[i][j]
            }
        }

        if (tempArR.any { it.contains(reg) } || tempArL.any{ it.contains(reg) }) return true
        return false
    }

    private fun checkRow(reg: String): Boolean {
        for (i in 0 until row) {
            if (board[i].joinToString("").contains(reg))
                return true
        }
        return false
    }

    private fun checkWinner(): Int {
        return if (checkRow("oooo") || checkColumn("oooo") || checkDiagonal("oooo")) 1
        else if (checkRow("****") || checkColumn("****") || checkDiagonal("****"))  2
        else 0
    }

    fun startGame() {
        val tempRowsN = Array(col) { row }
        var winner: Int
        var count = 0
        while (true) {
            println((if (countMove % 2 == 0) firstPlayer else secondPlayer) + "'s turn:")
            val move = readln()
            if (move.lowercase() == "end") {
                exit = true
                break
            }
            else if (!move.matches(Regex("\\d+"))) println("Incorrect column number")
            else if( move.toInt() < 1 || move.toInt() > col) println("The column number is out of range (1 - $col)")
            else {
                if (tempRowsN[move.toInt() - 1] == 0) println("Column $move is full")
                else {
                    if (countMove % 2 == 0) board[tempRowsN[move.toInt() - 1] - 1][move.toInt() - 1] = "o"
                    else board[tempRowsN[move.toInt() - 1] - 1][move.toInt() - 1] = "*"
                    tempRowsN[move.toInt() - 1]--
                    countMove++
                    count++
                    printBoard()
                    winner = checkWinner()
                    when {
                        winner == 1 -> {
                            println("Player $firstPlayer won")
                            scoreFP += 2
                            break
                        }
                        winner == 2 -> {
                            println("Player $secondPlayer won")
                            scoreSP += 2
                            break
                        }
                        count == row * col -> {
                            println("It is a draw")
                            scoreFP += 1
                            scoreSP += 1
                            break
                        }
                    }
                }
            }
        }
        if (numberOfGames == 1) exit = true
        else {
            println("Score")
            println("$firstPlayer: $scoreFP $secondPlayer: $scoreSP")
            if (gameNumber == numberOfGames) exit = true
            else {
                println("Game #${++gameNumber}")
                board = Array(row + 1) { Array(col + 1) { " " } }
                printBoard()
            }
        }
    }
}