
class RatesAdjuster {

    private List<Tuple> splitRates(Date snapshotDate, Tuple initial, Tuple current) {
        def (initialDateRange, initialRate) = initial
        def (currentDateRange, currentRate) = current
        [
            new Tuple((initialDateRange.first()..snapshotDate.previous()), initialRate),
            new Tuple((snapshotDate..currentDateRange.last()), currentRate)
        ]
    }

    def adjust(Date snapshotDate, List<Tuple> initialRates, List<Tuple> currentRates = []) {
        if(!currentRates) {
            return initialRates
        }

        def pastRatesWithSnapshot = initialRates.findAll { dateRange, rate ->
            dateRange.last() < snapshotDate || dateRange.contains(snapshotDate)
        }
        def futureRatesWithSnapshot = currentRates.findAll { dateRange, rate ->
            dateRange.first() > snapshotDate || dateRange.contains(snapshotDate)
        }

        def pastRateWithSnapshot = pastRatesWithSnapshot.last()
        def futureRateWithSnapshot = futureRatesWithSnapshot.first()
        def splitRates = splitRates(snapshotDate, pastRateWithSnapshot, futureRateWithSnapshot)

        def pastRates = pastRatesWithSnapshot.take(pastRatesWithSnapshot.size() - 1)
        def futureRates = futureRatesWithSnapshot.tail()
        pastRates + splitRates + futureRates
    }
}
