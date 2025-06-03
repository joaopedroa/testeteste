package br.com.itau.geradornotafiscal.core.enums;


public enum Regiao {
    NORTE{
        @Override
        public double calcularValorFrete(double valorFrete) {
            return valorFrete * 1.08;
        }
    },
    NORDESTE{
        @Override
        public double calcularValorFrete(double valorFrete) {
            return valorFrete * 1.085;
        }
    },
    CENTRO_OESTE{
        @Override
        public double calcularValorFrete(double valorFrete) {
            return valorFrete * 1.07;
        }
    },
    SUDESTE{
        @Override
        public double calcularValorFrete(double valorFrete) {
            return valorFrete * 1.048;
        }
    },
    SUL{
        @Override
        public double calcularValorFrete(double valorFrete) {
            return valorFrete * 1.06;
        }
    };

    public abstract double calcularValorFrete(double valorFrete);
}
