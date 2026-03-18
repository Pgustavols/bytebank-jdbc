import { ContaProps, DadosAberturaConta } from '../types'

const BASE_URL = 'https://bytebank-jdbc-production.up.railway.app/contas'

export async function listarContas(): Promise<ContaProps[]> {
  const res = await fetch(BASE_URL)
  if (!res.ok) throw new Error('Erro ao listar contas')
  return res.json()
}

export async function abrirConta(dados: DadosAberturaConta): Promise<void> {
  const res = await fetch(BASE_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dados)
  })
  if (!res.ok) throw new Error('Erro ao abrir conta')
}

export async function depositar(numero: number, valor: number): Promise<void> {
  const res = await fetch(`${BASE_URL}/${numero}/depositar?valor=${valor}`, {
    method: 'POST'
  })
  if (!res.ok) throw new Error('Erro ao realizar depósito')
}

export async function sacar(numero: number, valor: number): Promise<void> {
  const res = await fetch(`${BASE_URL}/${numero}/sacar?valor=${valor}`, {
    method: 'POST'
  })
  if (!res.ok) throw new Error('Saldo insuficiente ou erro ao realizar saque')
}

export async function transferir(pagador: number, beneficiado: number, valor: number): Promise<void> {
  const res = await fetch(`${BASE_URL}/transferir?pagador=${pagador}&beneficiado=${beneficiado}&valor=${valor}`, {
    method: 'POST'
  })
  if (!res.ok) throw new Error('Erro ao realizar transferência')
}

export async function encerrarConta(numero: number): Promise<void> {
  const res = await fetch(`${BASE_URL}/${numero}`, {
    method: 'DELETE'
  })
  if (!res.ok) throw new Error('Erro ao encerrar conta. Verifique se o saldo é zero')
}