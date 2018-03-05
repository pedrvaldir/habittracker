package com.example.valdir.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by VALDIR on 01/03/2018.
 */

public final class HabitContract {

    private HabitContract(){}

    public static final class HabitEntry implements BaseColumns {

        /** Nome da tabela do banco de dados */
        public final static String TABLE_NAME = "records";

        /**
         * Identificador único
         * Tipo: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Nome do hábito
         * Type: STRING
         */
        public final static String COLUMN_HABIT_NAME ="name";

        /**
         * Tempo de duração
         * Type: INTEGER
         */
        public final static String COLUMN_HABIT_TIME ="time";
    }
}