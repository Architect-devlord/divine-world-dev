package com.divineworld.personality;

public class MBTITraits {
    public enum Trait { I, E, S, N, T, F, J, P }
    private final Trait[] traits = new Trait[4]; // [I/E, S/N, T/F, J/P]

    public MBTITraits(Trait a, Trait b, Trait c, Trait d) {
        traits[0] = a; traits[1] = b; traits[2] = c; traits[3] = d;
    }

    public static MBTITraits randomTraits() {
        return new MBTITraits(
            Math.random() < 0.5 ? Trait.I : Trait.E,
            Math.random() < 0.5 ? Trait.S : Trait.N,
            Math.random() < 0.5 ? Trait.T : Trait.F,
            Math.random() < 0.5 ? Trait.J : Trait.P
        );
    }

    public static MBTITraits inheritTraits(MBTITraits mother, MBTITraits father) {
        Trait a = Math.random() < 0.5 ? mother.traits[0] : father.traits[0];
        Trait b = Math.random() < 0.5 ? mother.traits[1] : father.traits[1];
        Trait c = Math.random() < 0.5 ? mother.traits[2] : father.traits[2];
        Trait d = Math.random() < 0.5 ? mother.traits[3] : father.traits[3];
        return new MBTITraits(a,b,c,d);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Trait t : traits) sb.append(t.name());
        return sb.toString();
    }
}
