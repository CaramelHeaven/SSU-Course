fun main(args: Array<String>) {
    val firstThread = ArrayList<Double>()
    val secondThread = ArrayList<Double>()
    val listOfY = ArrayList<Double>()
    val lyambda = 0.3

    //variable
    val pointsFirst =ArrayList<Double>()
    val pointsSecond = ArrayList<Double>()

    for (i in 0..999) {
        val temp = Math.random()
        when (temp) {
            in 0.0..0.75 -> firstThread.add(-1 / lyambda * Math.log(Math.random()))
            in 0.75..1.0 -> secondThread.add(-1 / lyambda * Math.log(Math.random()))
            else -> "Nothing :<"
        }
    }

    for (i in 0 until firstThread.size - 1) {
        pointsFirst.add(Math.abs(firstThread[i + 1] - firstThread[i]))
    }

    for (i in 0 until secondThread.size - 1) {
        pointsSecond.add(Math.abs(secondThread[i + 1] - secondThread[i]))
    }

    pointsFirst.sort()
    pointsSecond.sort()

    pointsFirst.reverse()
    pointsSecond.reverse()

    firstThread.clear()
    secondThread.clear()

    for (r in pointsFirst.indices) {
        firstThread.add(pointsFirst[r] * pointsFirst[0] / 20)
    }

    for (r in pointsFirst.indices) {
        secondThread.add(pointsFirst[r] * pointsSecond[0] / 20)
    }

    //set y points
    var t = 0.0
    for (i in firstThread.indices) {
        listOfY.add(t)
        t = t + 0.1
    }

    val frame = DiagramsFrame()
    frame.drawFrame(firstThread, listOfY, secondThread)
}
