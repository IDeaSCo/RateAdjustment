import spock.lang.Specification

class RateAdjusterSpecs extends Specification {
    def "inital rate does not produce any adjusted rate"() {
        given: 'initial rate'
            def dateRange = (new Date('1-Jan-2014')..new Date('31-Dec-2014'))
            def rate = 230
            def snapshotDate = new Date('1-May-2014')
            def initialRate = new Tuple(dateRange, rate, snapshotDate)

        and: 'a rate adjuster'
            def rateAdjuster = new RateAdjuster()

        when: 'I adjust the rates'
            def adjustedRate = rateAdjuster.adjust(initialRate)

        then: 'I should see initial rate as base rate'
          adjustedRate == [initialRate]

    }

    def "current rate produces adjusted rate"() {
        given: 'initial rate'
            def dateRange = (new Date('1-Jan-2014')..new Date('31-Dec-2014'))
            def rate = 230
            def snapshotDate = new Date('1-May-2014')
            def initialRate = new Tuple(dateRange, rate, snapshotDate)

        and: 'current rate and snap shot date'
            def currentSnapshotDate = new Date('2-May-2014')
            def currentRate = new Tuple(dateRange, 150, currentSnapshotDate)

        and: 'a rate adjuster'
            def rateAdjuster = new RateAdjuster()

        when: 'I adjust the rates'
            def adjustedRates = rateAdjuster.adjust(initialRate, currentRate)

        then: 'I should see the adjusted base rates'
            adjustedRates == [new Tuple((new Date('1-Jan-2014')..new Date('1-May-2014')), 230),
                             new Tuple((new Date('2-May-2014')..new Date('31-Dec-2014')), 150)]
    }

//    def "initial rates with 2 seasons produces adjusted rates"() {
//
//    }
}
