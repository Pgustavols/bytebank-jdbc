import { useState } from 'react'
import { Modal } from '../modal'
import { abrirConta } from '../../../services/api'
import toast from 'react-hot-toast'

interface ModalAbrirContaProps {
  onClose: () => void
  onSucesso: () => void
}

export function ModalAbrirConta({ onClose, onSucesso }: ModalAbrirContaProps) {
  const [numero, setNumero] = useState('')
  const [nome, setNome] = useState('')
  const [cpf, setCpf] = useState('')
  const [email, setEmail] = useState('')
  const [loading, setLoading] = useState(false)


  async function handleConfirmar() {
    if (!numero || !nome || !cpf || !email) {
      setError('Preencha todos os campos')
      return
    }
    setError(null)
    setLoading(true)
    try {
      await abrirConta({
      numero: parseInt(numero),
      dadosCliente: { nome, cpf, email }
    })
    toast.success("Conta criada com sucesso")
    onSucesso()
    onClose()
    } catch (e: unknown) {
      toast.error(e instanceof Error ? e.message : 'Ocorreu um Erro')
    } finally {
      setLoading(false)
    }
  }

  return (
    <Modal titulo="Abrir conta" onClose={onClose}>
      <div className="flex flex-col gap-4">
        <div>
          <label className="text-gray-400 text-sm mb-1 block">Número da conta</label>
          <input
            type="number"
            value={numero}
            onChange={e => setNumero(e.target.value)}
            placeholder="Ex: 12345"
            className="w-full bg-gray-800 border border-gray-700 rounded-xl p-3 text-white focus:outline-none focus:border-purple-800 transition-colors"
          />
        </div>
        <div>
          <label className="text-gray-400 text-sm mb-1 block">Nome completo</label>
          <input
            type="text"
            value={nome}
            onChange={e => setNome(e.target.value)}
            placeholder="Ex: João Silva"
            className="w-full bg-gray-800 border border-gray-700 rounded-xl p-3 text-white focus:outline-none focus:border-purple-800 transition-colors"
          />
        </div>
        <div>
          <label className="text-gray-400 text-sm mb-1 block">CPF</label>
          <input
            type="text"
            value={cpf}
            onChange={e => setCpf(e.target.value)}
            placeholder="Ex: 12345678901"
            className="w-full bg-gray-800 border border-gray-700 rounded-xl p-3 text-white focus:outline-none focus:border-purple-800 transition-colors"
          />
        </div>
        <div>
          <label className="text-gray-400 text-sm mb-1 block">Email</label>
          <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            placeholder="Ex: joao@email.com"
            className="w-full bg-gray-800 border border-gray-700 rounded-xl p-3 text-white focus:outline-none focus:border-purple-800 transition-colors"
          />
        </div>
        
        <button
          onClick={handleConfirmar}
          disabled={loading}
          className="w-full bg-purple-800 hover:bg-purple-700 text-white font-semibold py-3 rounded-xl transition-colors disabled:opacity-50"
        >
          {loading ? 'Abrindo conta...' : 'Abrir conta'}
        </button>
      </div>
    </Modal>
  )
}