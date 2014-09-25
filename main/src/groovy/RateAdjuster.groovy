import groovy.time.TimeCategory

class RateAdjuster {
    def adjust(Date snapshotDate, List<Tuple> initialRates, List<Tuple> currentRates = []) {
        if(!currentRates) {
            return initialRates
        }

        def adjustedRates = []

        initialRates.each { dateRange, rate ->
            if (dateRange.contains(snapshotDate)) {
                adjustedRates << new Tuple((dateRange.first()..snapshotDate.previous()), rate)
            } else {
                if(dateRange.last() < snapshotDate)
                    adjustedRates << new Tuple(dateRange, rate)
            }
        }

        currentRates.each { dateRange, rate ->
            if (dateRange.contains(snapshotDate)){
                adjustedRates << new Tuple((snapshotDate..dateRange.last()), rate)
            }else{
                if ((dateRange.first() > snapshotDate))
                    adjustedRates << new Tuple(dateRange, rate)
            }

        }
        adjustedRates
    }
}
