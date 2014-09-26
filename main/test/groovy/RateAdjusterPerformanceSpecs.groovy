import spock.lang.Specification

class RateAdjusterPerformanceSpecs extends Specification{

    def "Time taken to process 5 season data for 375 room types"() {
        given: 'initial rate'
            def zeroSeason = (new Date('1-Jan-2004')..new Date('31-Dec-2013'))
            def firstSeason = (new Date('1-Jan-2014')..new Date('1-May-2014'))
            def secondSeason = (new Date('2-May-2014')..new Date('15-Nov-2014'))
            def thirdSeason = (new Date('16-Nov-2014')..new Date('31-Dec-2014'))
            def fourthSeason = (new Date('1-Jan-2015')..new Date('31-Dec-2019'))
            def initialRates = [new Tuple(zeroSeason, 350),
                                new Tuple(firstSeason, 100),
                                new Tuple(secondSeason, 200),
                                new Tuple(thirdSeason, 300),
                                new Tuple(fourthSeason, 400)]

        and: 'current rate and snapshot date'
            def currentZeroSeason = new Tuple(zeroSeason, 120)
            def currentFirstSeason = new Tuple(firstSeason, 180)
            def currentSecondSeason = new Tuple(secondSeason, 200)
            def currentThirdSeason = new Tuple(thirdSeason, 380)
            def currentFourthSeason = new Tuple(fourthSeason, 390)

            def currentRates = [currentZeroSeason,
                                currentFirstSeason,
                                currentSecondSeason,
                                currentThirdSeason,
                                currentFourthSeason]
            def snapShotDate = new Date('10-May-2014')

        when: 'I adjust the rates for 375 room types'
            def startTime = System.currentTimeMillis()
            def adjustedRates = new RatesAdjuster()
            375.times {
                adjustedRates.adjust(snapShotDate, initialRates, currentRates)
            }

        then: 'I should get the total time taken'
            def timeTaken = System.currentTimeMillis() - startTime
            println "Time Taken: ${timeTaken/1000} secs"
            timeTaken < 60 * 1000
    }
}
