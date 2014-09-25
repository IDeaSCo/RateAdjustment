class RateAdjuster {
    def adjust(Tuple initial, Tuple current = new Tuple()) {
        if(!current) {
            return [initial]
        }

        def (currentDateRange, currentRate, snapshotDate) = current
        def (initialDateRange, initialRate) = initial
        [new Tuple((initialDateRange.first()..snapshotDate.previous()), initialRate),
         new Tuple((snapshotDate..currentDateRange.last()), currentRate)]
    }
}
