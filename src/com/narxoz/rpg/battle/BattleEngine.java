package com.narxoz.rpg.battle;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public final class BattleEngine {
    private static BattleEngine instance;
    private Random random = new Random(1L);
    private BattleEngine() {
    }
    public static BattleEngine getInstance() {
        if (instance == null) {
            instance = new BattleEngine();
        }
        return instance;
    }
    public BattleEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }
    public void reset() {
        this.random = new Random(1L);
    }

    public EncounterResult runEncounter(List<Combatant> teamA, List<Combatant> teamB) {
        if (teamA == null || teamB == null) {
            throw new IllegalArgumentException("Team list is null");
        }

        if (teamA.isEmpty() || teamB.isEmpty()) {
            throw new IllegalArgumentException("No players in team");
        }
        List<Combatant> teamACopy = new ArrayList<>(teamA);
        List<Combatant> teamBCopy = new ArrayList<>(teamB);

        EncounterResult result = new EncounterResult();
        int rounds = 0;

        result.addLog("Battle Started");

        while (!teamACopy.isEmpty() && !teamBCopy.isEmpty()) {
            rounds++;
            result.addLog("Round " + rounds);
            executeRound(teamACopy, teamBCopy, result);
            if (teamBCopy.isEmpty()) {
                break;
            }
            executeRound(teamBCopy, teamACopy, result);
        }
        String winner = teamACopy.isEmpty() ? "Team B" : "Team A";
        result.setWinner(winner);
        result.setRounds(rounds);
        result.addLog("Winner: " + winner);
        return result;
    }
    private void executeRound(List<Combatant> attackers, List<Combatant> defenders, EncounterResult result) {
        for (Combatant attacker : new ArrayList<>(attackers)) {
            if (defenders.isEmpty()) {
                return;
            }
            Combatant target = defenders.get(random.nextInt(defenders.size()));
            int damage = attacker.getAttackPower();
            result.addLog(attacker.getName() + " attacks " + target.getName() + " for " + damage);
            target.takeDamage(damage);

            if (!target.isAlive()) {
                result.addLog(target.getName() + " has fallen.");
                defenders.remove(target);
            }
        }
    }
}
