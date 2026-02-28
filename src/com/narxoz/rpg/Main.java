package com.narxoz.rpg;

import com.narxoz.rpg.adapter.EnemyCombatantAdapter;
import com.narxoz.rpg.adapter.HeroCombatantAdapter;
import com.narxoz.rpg.battle.BattleEngine;
import com.narxoz.rpg.battle.Combatant;
import com.narxoz.rpg.battle.EncounterResult;
import com.narxoz.rpg.enemy.BasicEnemy;
import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.enemy.Goblin;
import com.narxoz.rpg.hero.Hero;
import com.narxoz.rpg.hero.Mage;
import com.narxoz.rpg.hero.Warrior;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RPG Battle Engine Demo ===");
        BattleEngine engine1 = BattleEngine.getInstance();
        BattleEngine engine2 = BattleEngine.getInstance();
        System.out.println("Same instance? " + (engine1 == engine2));
        System.out.println();
        engine1.setRandomSeed(42L);

        Hero warrior = new Warrior("Arthas");
        Hero mage = new Mage("Jaina");

        Enemy goblin = new BasicEnemy("Goblin", 15, 60);
        Enemy orc = new BasicEnemy("Orc", 20, 90);

        List<Combatant> teamA = new ArrayList<>();
        teamA.add(new HeroCombatantAdapter(warrior));
        teamA.add(new HeroCombatantAdapter(mage));

        List<Combatant> teamB = new ArrayList<>();
        teamB.add(new EnemyCombatantAdapter(goblin));
        teamB.add(new EnemyCombatantAdapter(orc));

        EncounterResult result = engine1.runEncounter(teamA, teamB);

        System.out.println("=== Battle Result ===");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        System.out.println("--- Battle Log ---");
        for (String log : result.getBattleLog()) {
            System.out.println(log);
        }
        System.out.println();
        System.out.println("=== Demo Complete ===");
    }
}
