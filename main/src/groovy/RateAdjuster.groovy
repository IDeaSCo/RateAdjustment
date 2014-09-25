import groovy.time.TimeCategory

class RateAdjuster {
    def adjustFor(Date snapshotDate, List<Tuple> initialRates, Tuple current = new Tuple()) {
        if(!current) {
            return initialRates
        }

        def (currentDateRange, currentRate) = current
        def adjustedRates = []
        initialRates.each { dateRange, rate ->
            if (dateRange.contains(snapshotDate)) {
                use(TimeCategory) {
                    adjustedRates << new Tuple((dateRange.first()..snapshotDate.previous()), rate)
                    adjustedRates << new Tuple((snapshotDate..currentDateRange.last()), currentRate)
                }
            } else {
                adjustedRates << new Tuple(dateRange, rate)
            }
        }
        adjustedRates
    }
}
