import spock.lang.Specification

class RateAdjusterSpecs extends Specification {
    private final rateAdjuster = new RateAdjuster()

    def "inital rate does not produce any adjusted rate"() {
        given: 'initial rate'
            def dateRange = (new Date('1-Jan-2014')..new Date('31-Dec-2014'))
            def rate = 230
            def initialRate = [new Tuple(dateRange, rate)]

        when: 'I adjust the rates for snapshot date'
            def snapshotDate = new Date('1-May-2014')
            def adjustedRates = rateAdjuster.adjustFor(snapshotDate, initialRate)

        then: 'I should see initial rate as base rate'
          adjustedRates == initialRate

    }

    def "current rate produces adjusted rate"() {
        given: 'initial rate'
            def dateRange = (new Date('1-Jan-2014')..new Date('31-Dec-2014'))
            def rate = 230
            def initialRate = [new Tuple(dateRange, rate)]

        and: 'current rate and snap shot date'
            def snapshotDate = new Date('2-May-2014')
            def currentRate = new Tuple(dateRange, 150)

        when: 'I adjust the rates for snapshot date'
            def adjustedRates = this.rateAdjuster.adjustFor(snapshotDate, initialRate, currentRate)

        then: 'I should see the adjusted base rates'
            adjustedRates == [new Tuple((new Date('1-Jan-2014')..new Date('1-May-2014')), 230),
                             new Tuple((new Date('2-May-2014')..new Date('31-Dec-2014')), 150)]
    }

    def "initial rates with 2 seasons produces adjusted rates for snapshot date in one of the seasons"() {
        given: 'initial rate'
            def firstSeason = (new Date('1-Jan-2014')..new Date('1-May-2014'))
            def secondSeason = (new Date('2-May-2014')..new Date('31-Dec-2014'))
            def initialRates = [new Tuple(firstSeason, 230),
                                new Tuple(secondSeason, 150)]

        and: 'current rate and snapshot date within the second season'
            def currentRate = new Tuple((new Date('1-Jan-2014')..new Date('31-Dec-2014')), 180)
            def snapShotDate = new Date('3-May-2014')

        when: 'I adjust the rates'
            def adjustedRates = rateAdjuster.adjustFor(snapShotDate, initialRates, currentRate)

        then: 'I should see the adjusted base rates'
            adjustedRates == [new Tuple(firstSeason, 230),
                              new Tuple((new Date('2-May-2014')..new Date('2-May-2014')), 150),
                              new Tuple((new Date('3-May-2014')..new Date('31-Dec-2014')), 180)]
    }

    def "date change in current Rate produces adjusted rates for the snapshot date in a shortened season"() {
        given: 'initial rate'
            def firstSeason = (new Date('1-Jan-2014')..new Date('1-May-2014'))
            def secondSeason = (new Date('2-May-2014')..new Date('31-Dec-2014'))
            def initialRates = [new Tuple(firstSeason, 230),
                                new Tuple(secondSeason, 150)]

        and: 'current rate and snapshot date within second shortened season'
            def currentRate = new Tuple((new Date('1-Jan-2014')..new Date('30-Nov-2014')), 180)
            def snapShotDate = new Date('3-May-2014')

        when: 'I adjust the rates'
            def adjustedRates = rateAdjuster.adjustFor(snapShotDate, initialRates, currentRate)

        then: 'I should see the adjusted base rates'
            adjustedRates == [new Tuple(firstSeason, 230),
                              new Tuple((new Date('2-May-2014')..new Date('2-May-2014')), 150),
                              new Tuple((new Date('3-May-2014')..new Date('30-Nov-2014')), 180)]
    }

//    def "initial and current rates with seasons produces adjusted rates"() {
//        given: 'initial rate'
//
//    }

}
