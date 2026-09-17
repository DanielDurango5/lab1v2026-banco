fetch('http://localhost:8088/api/customers') .then(response => response.json()) .then(data => console.log(data)) .catch(error => console.error('Error:', error))
const API_URL = 'http://localhost:8088/api';

//Manejo de pestañas
function mostrarVista(vista) {
    document.querySelectorAll('.view').forEach(v => v.classList.remove('active'));
    document.querySelectorAll('.tabs button').forEach(b => b.classList.remove('active'));

    document.getElementById(vista).classList.add('active');
    document.getElementById('btn' + vista.charAt(0).toUpperCase() + vista.slice(1)).classList.add('active');

    if (vista === 'clientes') cargarClientes();
}

//Vista 1: Consultar clientes
function cargarClientes() {
    fetch(`${API_URL}/customers`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('tablaClientes');
            tbody.innerHTML = '';
            data.forEach(c => {
                tbody.innerHTML += `
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.firstName}</td>
                        <td>${c.lastName}</td>
                        <td>${c.accountNumber}</td>
                        <td>${c.balance}</td>
                    </tr>`;
            });
        })
        .catch(error => console.error('Error al cargar clientes:', error));
}

//Vista 2: Transferir dinero
function transferirDinero() {
    const senderAccountNumber = document.getElementById('senderAccount').value;
    const receiverAccountNumber = document.getElementById('receiverAccount').value;
    const amount = parseFloat(document.getElementById('amount').value);
    const msg = document.getElementById('transferMsg');

    if (!senderAccountNumber || !receiverAccountNumber || !amount) {
        msg.style.color = 'red';
        msg.textContent = 'Completa todos los campos.';
        return;
    }

    fetch(`${API_URL}/transactions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ senderAccountNumber, receiverAccountNumber, amount })
    })
        .then(async response => {
            const data = await response.json().catch(() => null);
            if (!response.ok) {
                throw new Error(typeof data === 'string' ? data : 'Error en la transferencia.');
            }
            return data;
        })
        .then(data => {
            msg.style.color = 'green';
            msg.textContent = `Transferencia exitosa. ID transacción: ${data.id}`;
        })
        .catch(error => {
            msg.style.color = 'red';
            msg.textContent = error.message;
        });
}

//Vista 3: Histórico de transacciones
function consultarHistorico() {
    const accountNumber = document.getElementById('cuentaHistorico').value;
    const msg = document.getElementById('historicoMsg');
    const tbody = document.getElementById('tablaHistorico');
    tbody.innerHTML = '';

    if (!accountNumber) {
        msg.style.color = 'red';
        msg.textContent = 'Ingresa un número de cuenta.';
        return;
    }

    fetch(`${API_URL}/transactions/${accountNumber}`)
        .then(response => response.json())
        .then(data => {
            if (data.length === 0) {
                msg.style.color = 'orange';
                msg.textContent = 'No hay transacciones para esta cuenta.';
                return;
            }
            msg.textContent = '';
            data.forEach(t => {
                tbody.innerHTML += `
                    <tr>
                        <td>${t.id}</td>
                        <td>${t.senderAccountNumber}</td>
                        <td>${t.receiverAccountNumber}</td>
                        <td>${t.amount}</td>
                        <td>${t.timestamp}</td>
                    </tr>`;
            });
        })
        .catch(error => {
            msg.style.color = 'red';
            msg.textContent = 'Error al consultar el histórico.';
            console.error(error);
        });
}

// Cargar clientes al abrir la página
document.addEventListener('DOMContentLoaded', cargarClientes);