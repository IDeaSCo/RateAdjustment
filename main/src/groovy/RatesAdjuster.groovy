
class RatesAdjuster {

    private List<Tuple> splitRates(Date snapshotDate, Tuple initial, Tuple current) {
        def (initialDateRange, initialRate) = initial
        def (currentDateRange, currentRate) = current
        if (initial == current) {
            return [current]
        }

        [
            new Tuple((initialDateRange.first()..snapshotDate.previous()), initialRate),
            new Tuple((snapshotDate..currentDateRange.last()), currentRate)
        ]
    }

    def adjust(Date snapshotDate, List<Tuple> initialRates, List<Tuple> currentRates = []) {
        if(!currentRates) {
            return initialRates
        }

        def (pastRates, pastRatesWithSnapshot) = initialRates.split { dateRange, rate ->
            dateRange.last() < snapshotDate
        }
        def (futureRates, futureRatesWithSnapshot) = currentRates.split { dateRange, rate ->
            dateRange.first() > snapshotDate
        }
        def pastRateWithSnapshot = pastRatesWithSnapshot.first()
        def futureRateWithSnapshot = futureRatesWithSnapshot.last()
        def splitRates = splitRates(snapshotDate, pastRateWithSnapshot, futureRateWithSnapshot)
        pastRates + splitRates + futureRates
    }
}
