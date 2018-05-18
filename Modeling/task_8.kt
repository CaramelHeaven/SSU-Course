fun main(args: Array<String>) {
    var baseElement1: Double
    var baseElement2: Double
    var reserv: Double

    val lyambda = 0.01

    var fullTime = 0.0
    var mainTime = 0.0

    for (i in 0..999) {
        val temp = Math.random()
        baseElement1 = (-1 / lyambda) * Math.log(temp)

        val temp1 = Math.random()
        baseElement2 = (-1 / lyambda) * Math.log(temp1)

        val temp2 = Math.random()
        reserv = (-1 / lyambda) * Math.log(temp2)

        if (baseElement1 > baseElement2) {
            fullTime = fullTime + baseElement1 + reserv
        } else {
            mainTime = mainTime + baseElement2
            fullTime = fullTime + baseElement2 + reserv
        }
    }

    println("Мат ожидание TTL system: " + fullTime / 1000)
    println("Мат ожидание TTL до отказа второго основного элемента: " + mainTime / 1000)
}
