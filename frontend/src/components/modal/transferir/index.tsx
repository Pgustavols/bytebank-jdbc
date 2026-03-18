import { useState } from 'react'
import { Modal } from '../modal'
import { transferir } from '../../../services/api'
import toast from 'react-hot-toast'

interface ModalTransferirProps {
  numeroConta: number
  onClose: () => void
  onSucesso: () => void
}

export function ModalTransferir({ numeroConta, onClose, onSucesso }: ModalTransferirProps) {
  const [beneficiado, setBeneficiado] = useState('')
  const [valor, setValor] = useState('')
  const [loading, setLoading] = useState(false)


  async function handleConfirmar() {
    if (!valor || !beneficiado) return
    setLoading(true)
    try{
       await transferir(numeroConta, parseInt(beneficiado), parseFloat(valor))
       toast.success('Transferência realizada com suceso ')
       onSucesso()
       onClose()
    } catch (e: unknown) {
      toast.error(e instanceof Error ? e.message : 'Ocorreu um erro' )
    } finally {
      setLoading(false)
    }
  }

  return (
    <Modal titulo="Transferir" onClose={onClose}>
      <div className="flex flex-col gap-4">
        <div>
          <label className="text-gray-400 text-sm mb-1 block">Conta destino</label>
          <input
            type="number"
            value={beneficiado}
            onChange={e => setBeneficiado(e.target.value)}
            placeholder="Número da conta"
            className="w-full bg-gray-800 border border-gray-700 rounded-xl p-3 text-white focus:outline-none focus:border-purple-800 transition-colors"
          />
        </div>
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
          {loading ? 'Processando...' : 'Confirmar transferência'}
        </button>
      </div>
    </Modal>
  )
}