import { useState } from 'react'
import { Modal } from '../modal'
import { depositar } from '../../../services/api'

import toast from 'react-hot-toast'

interface ModalDepositarProps {
  numeroConta: number
  onClose: () => void
  onSucesso: () => void
}

export function ModalDepositar({ numeroConta, onClose, onSucesso }: ModalDepositarProps) {
  const [valor, setValor] = useState('')
  const [loading, setLoading] = useState(false)


  async function handleConfirmar() {
    if (!valor) return
    setError(null)
    setLoading(true)
    try {
      await depositar(numeroConta, parseFloat(valor))
      console.log('sucesso')
      toast.success('Depósito realizado com sucesso!')
      onSucesso()
      onClose()
    } catch (e: unknown) {
      console.log('erro', e)
      toast.error(e instanceof Error ? e.message : 'Ocorreu um erro')
    } finally {
      setLoading(false)
    }
   
  }

  return (
    <Modal titulo="Depositar" onClose={onClose}>
      <div className="flex flex-col gap-4">
        <div>
          <label className="text-gray-400 text-sm mb-1 block">Valor</label>
          <input
            type="number"
            value={valor}
            onChange={e => setValor(e.target.value)}
            placeholder="R$ 0,00"
            className="w-full bg-gray-800 border border-gray-700 rounded-xl p-3 text-white focus:outline-none focus:border-purple-800 transition-colors"
          />
        </div>
        <button
          onClick={handleConfirmar}
          disabled={loading}
          className="w-full bg-purple-800 hover:bg-purple-700 text-white font-semibold py-3 rounded-xl transition-colors disabled:opacity-50"
        >
          {loading ? 'Processando...' : 'Confirmar depósito'}
        </button>
      </div>
    </Modal>
  )
}