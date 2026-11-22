export function renderMesaCard(mesa, onClick) {
  const div = document.createElement('div');
  const estadoClass = mesa.estado === 'LIBRE' ? 'libre' : 'ocupada';
  
  div.className = `mesa-card ${estadoClass}`;
  div.innerHTML = `
    <span class="mesa-numero">${mesa.numero}</span>
    <span class="mesa-estado">${mesa.estado}</span>
  `;
  
  div.addEventListener('click', () => onClick(mesa));
  return div;
}
