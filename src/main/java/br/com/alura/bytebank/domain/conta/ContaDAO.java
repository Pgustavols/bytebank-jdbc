package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.RegraDeNegocioException;
import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {
    protected Connection conn;
    ContaDAO(Connection connection){
        this.conn = connection;
    }

    public void salvar(DadosAberturaConta dadosDaConta){
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), cliente);
        String sql = "insert into conta(numero, saldo, cliente_nome, cliente_cpf, cliente_email) values(?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement =  conn.prepareStatement(sql)){

            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, dadosDaConta.dadosCliente().nome());
            preparedStatement.setString(4, dadosDaConta.dadosCliente().cpf());
            preparedStatement.setString(5, dadosDaConta.dadosCliente().email());

            preparedStatement.execute();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Set<Conta> listar(){
        Set<Conta> contas = new HashSet<>();

        String sql = "SELECT * FROM conta";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            try(ResultSet resultSet= ps.executeQuery()){
                while(resultSet.next()){
                    Integer numero = resultSet.getInt(1);
                    BigDecimal saldo = resultSet.getBigDecimal(2);
                    String nome = resultSet.getString(3);
                    String cpf = resultSet.getString(4);
                    String email = resultSet.getString(5);
                    Cliente cliente = new Cliente (new DadosCadastroCliente(nome, cpf, email));

                    Conta conta = new Conta(numero, cliente);
                    conta.depositar(saldo);
                    contas.add(conta);
                }
            }
            return contas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal buscarSaldo(Integer numeroDaConta){
        String sql = "SELECT saldo FROM CONTA WHERE numero = ?";
        try (PreparedStatement preparedStatement =  conn.prepareStatement(sql)){

            preparedStatement.setInt(1, numeroDaConta);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getBigDecimal(1);
            }

            return BigDecimal.ZERO;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public int depositar(Integer numeroDaConta, BigDecimal valor){
        String sql = "UPDATE conta SET saldo = saldo + ? WHERE numero = ?";

        try (PreparedStatement preparedStatement =  conn.prepareStatement(sql)){
            preparedStatement.setBigDecimal(1, valor);
            preparedStatement.setInt(2, numeroDaConta);

            return preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void sacar(Integer numeroDaConta, BigDecimal valor){
        String sql = "UPDATE conta SET saldo = saldo - ? WHERE numero = ?";

        try (PreparedStatement preparedStatement =  conn.prepareStatement(sql)){
            preparedStatement.setBigDecimal(1, valor);
            preparedStatement.setInt(2, numeroDaConta);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public int deletarConta(Integer numeroConta){
        String sql = "DELETE FROM CONTA WHERE SALDO = 0 and numero = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, numeroConta);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected boolean hasConta(Integer numero) {
        String sql = "SELECT 1 FROM CONTA WHERE numero = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numero);

            try (ResultSet resultSet = ps.executeQuery()){
                return resultSet.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
