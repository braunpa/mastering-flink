package de.vicio.example;

public enum OrtEnum {
    Konstanz{
        @Override
        public String toString(){
            return "Konstanz";
        }
    },
    Radolfzell{
        @Override
        public String toString(){
            return "Radolfzell";
        }
    },
    Villingen{
        @Override
        public String toString(){
            return "Villingen-Schwenningen";
        }
    },
    Feudentstadt{
        @Override
        public String toString(){
            return "Freundenstadt";
        }
    },
    Oppenau{
        @Override
        public String toString(){
            return "Oppenau";
        }
    }
}
