package cinema

enum class CINEMA_OPTIONS(val value: Int) {
    SHOW_THE_SEATS(1),
    BUY_A_TICKET(2),
    STATISTICS(3),
    EXIT(0);
}

class Cinema(val rows: Int, val seatsPerRow: Int) {
    val cinemaSeats = Array(rows) {Array(seatsPerRow + 1) {'0'} }
    var totalSeats = rows * seatsPerRow
    var currentIncome = 0

    init {
        for (row in 0 until rows) {
            cinemaSeats[row][0] = (row + 1).digitToChar()
            for (seat in 1..seatsPerRow) {
                cinemaSeats[row][seat] = 'S'
            }
        }
    }

    fun printCinemaSeats() {
        println("Cinema:")
        print(" ")
        for (seat in 1..seatsPerRow) {
            print(" $seat")
        }
        println()

        for (row in cinemaSeats) {
            for (seat in row) {
                print("$seat ")
            }
            println()
        }
    }

    fun totalIncome(): Int {
        val numberOfSeats = seatsPerRow * rows
        var income = 0
        if (numberOfSeats in 1..60) {
            val costPerTicket = 10
            income = numberOfSeats * costPerTicket
        } else {
            val costPerTicketFrontHalf = 10
            val costPerTicketBackHalf = 8
            income += (rows / 2) * seatsPerRow * costPerTicketFrontHalf
            income += (rows - rows / 2) * seatsPerRow * costPerTicketBackHalf
        }
        return income
    }

    fun taken(row: Int, seat: Int): Boolean {
        if (cinemaSeats[row][seat] == 'B') {
            return true
        } else {
            return false
        }
    }

    fun buyTicket() {
        println("Enter a row number: ")
        val rowNumber = readln().toInt()
        println("Enter a seat number in that row: ")
        val seatNumber = readln().toInt()

        if (rowNumber < 0 || rowNumber > rows || seatNumber < 0 || seatNumber > seatsPerRow) {
            println("Wrong input!")
            return buyTicket()
        } else if (this.taken(rowNumber - 1, seatNumber)) {
            println("\nThat ticket has already been purchased!\n")
            return buyTicket()
        }

        if (rows * seatsPerRow in 1..60) {
            println("Ticket price: $10")
            currentIncome += 10
        } else if (rowNumber <= rows / 2) {
            println("Ticket price: $10")
            currentIncome += 10
        } else {
            println("Ticket price: $8")
            currentIncome += 8
        }

        cinemaSeats[rowNumber - 1][seatNumber] = 'B'
    }

    fun statistics() {
        var soldTickets = 0
        for (row in cinemaSeats) {
            soldTickets += row.count { it == 'B' }
        }

        val percentage = (soldTickets.toDouble() / totalSeats.toDouble()) * 100
        val totalIncome = totalIncome()

        println("Number of purchased tickets: $soldTickets")
        println("Percentage: " + "%.2f%%".format(percentage))
        println("Current income: $$currentIncome")
        println("Total income: $$totalIncome")
    }

    fun menu(): Boolean {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        val userChoice = readln().toInt()

        when (userChoice) {
            CINEMA_OPTIONS.SHOW_THE_SEATS.value -> printCinemaSeats()
            CINEMA_OPTIONS.BUY_A_TICKET.value -> buyTicket()
            CINEMA_OPTIONS.STATISTICS.value -> statistics()
            CINEMA_OPTIONS.EXIT.value -> return true
            else -> println("Unknown Value")
        }
        println()
        return false
    }

}


fun main() {
    val cinema = initCinema()
    var exitProgram = false
    while (!exitProgram) {
        exitProgram = cinema.menu()
    }
}
fun initCinema(): Cinema {
    println("Enter the number of rows: ")
    val numberOfRows = readln().toInt()
    println("Enter the number of seats in each row:")
    val numberOfSeatsPerRow = readln().toInt()
    val cinema = Cinema(numberOfRows, numberOfSeatsPerRow)

    println()
    return cinema
}

