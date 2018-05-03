#experimental code

expCircle = 1.0
expSter = 0.99

otlkCircle = 0.01
otlkSter = 0.01

counter = 0

for i in 0..1000

    #our  sum
    sumSter = 0.0
    sumCircle = 0.0

    for q in 0..11
        temp = Random.rand(0.0...1.0)
        sumCircle = sumCircle + temp
    end

    for q in 0..11
        temp = Random.rand(0.0...1.0)
        sumSter = sumSter + temp
    end

    resultCircle = expCircle + otlkCircle * (sumCircle - 6)
    resultSter = expSter + otlkSter * (sumSter - 6)

    if resultCircle > resultSter
        counter++
    end

    puts " our result #{resultCircle} and resultSter #{resultSter}"

end

persent = counter / 1000
puts "persent: #{persent}"
