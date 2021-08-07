package xyz.shurlin.item.cultivation;

public class SpiritConsumeData {
    private long eachTickWhileInjecting;
    private long useForOnce;
    public static final SpiritConsumeData SIMPLE = new SpiritConsumeData(2,64);

    public SpiritConsumeData(long eachTickWhileInjecting, long useForOnce) {
        this.eachTickWhileInjecting = eachTickWhileInjecting;
        this.useForOnce = useForOnce;
    }

    public long getEachTickWhileInjecting() {
        return eachTickWhileInjecting;
    }

    public long getUseForOnce() {
        return useForOnce;
    }
}
