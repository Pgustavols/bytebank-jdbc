package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.ConnectionFactory;
import br.com.alura.bytebank.domain.RegraDeNegocioException;
import br.com.alura.bytebank.domain.cliente.Cliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaService {

    private ConnectionFactory connection;
    public ContaService(){
        this.connection = new ConnectionFactory();
    }

    public Set<Conta> listarContasAbertas() {
        try(Connection conn = connection.recuperarConexao()){
            ContaDAO contaDAO = new ContaDAO(conn);
            return contaDAO.listar();
        }catch (SQLException e){
            throw new RuntimeException("Erro ao comunicar com o banco de dados", e);
        }
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        try(Connection conn = connection.recuperarConexao()){
            ContaDAO contaDAO = new ContaDAO(conn);
            return contaDAO.buscarSaldo(numeroDaConta);
        }catch (SQLException e){
            throw new RuntimeException("Erro ao comunicar com o banco de dados", e);
        }

    }

    public void abrir(DadosAberturaConta dadosDaConta) {
        try(Connection conn = connection.recuperarConexao()){
            ContaDAO contaDAO = new ContaDAO(conn);
            contaDAO.salvar(dadosDaConta);

        }catch (SQLException e){
            throw new RuntimeException("Erro ao comunicar com o banco de dados", e);
        }

    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        try (Connection conn = connection.recuperarConexao()) {

            conn.setAutoCommit(false);

            try {
                ContaDAO contaDAO = new ContaDAO(conn);

                if (contaDAO.hasConta(numeroDaConta)) {
                    if (contaDAO.buscarSaldo(numeroDaConta).compareTo(valor) >= 0) {

                        contaDAO.sacar(numeroDaConta, valor);
                        System.out.println("Saque realizado com sucesso!");

                        // 2. TUDO DEU CERTO! Manda o banco salvar definitivamente.
                        conn.commit();

                    } else {
                        throw new RegraDeNegocioException("Saldo Insuficiente!");
                    }
                } else {
                    throw new RegraDeNegocioException("Não existe conta com esse número.");
                }

            } catch (Exception e) {
                conn.rollback();

                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao comunicar com o banco de dados", e);
        }
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }

        try(Connection conn = connection.recuperarConexao()){

            ContaDAO contaDAO = new ContaDAO(conn);
            int i = contaDAO.depositar(numeroDaConta, valor);

            if(i == 1){
                System.out.println("Depósito realizado com sucesso!");
            }else{
                throw new RegraDeNegocioException("Não existe conta com este número.");
            }

        }catch (SQLException e){
            throw new RuntimeException("Erro ao comunicar com o banco de dados", e);
        }
    }

    public void transferir(Integer numeroDaContaPagador, Integer numeroDaContaBeneficiado, BigDecimal valor){
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor da transferência deve ser superior a zero!");
        }
        try(Connection conn = connection.recuperarConexao()){
            conn.setAutoCommit(false);
            try{
                ContaDAO contaDAO = new ContaDAO(conn);

                if(!contaDAO.hasConta(numeroDaContaBeneficiado) || !contaDAO.hasConta(numeroDaContaPagador)){
                    throw new RegraDeNegocioException("Uma ou ambas as contas não existem!");
                }

                if (contaDAO.buscarSaldo(numeroDaContaPagador).compareTo(valor) < 0) {
                    throw new RegraDeNegocioException("Saldo Insuficiente para transferência!");
                }

                contaDAO.sacar(numeroDaContaPagador, valor);
                contaDAO.depositar(numeroDaContaBeneficiado, valor);
                conn.commit();
                System.out.println("Transferência realizada com sucesso!");

            }catch (Exception e) {
                conn.rollback();
                throw new RegraDeNegocioException("Erro durante a transferência. Operação cancelada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao comunicar com o banco de dados", e);
        }

    }

    public void encerrar(Integer numeroDaConta) {

        try(Connection conn = connection.recuperarConexao()){
            ContaDAO contaDAO = new ContaDAO(conn);
            if(contaDAO.hasConta(numeroDaConta)){
                if (contaDAO.deletarConta(numeroDaConta) == 1){
                    System.out.println("Conta deletada com sucesso");
                }else{
                    throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
                }
            }else{
                System.out.println("Não existe conta com esse número.");
            }
        }catch (SQLException e){
            throw new RuntimeException("Erro ao comunicar com o banco de dados", e);
        }
    }

}
