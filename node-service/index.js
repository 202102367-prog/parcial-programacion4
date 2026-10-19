const express = require('express');
const axios = require('axios');
const app = express();
const api = axios.create({ baseURL: process.env.SPRING_API_URL || 'http://localhost:8080', timeout: 10000 });
app.get('/resumen-citas', async (req, res) => {
  try {
    const { data: citas } = await api.get('/api/citas');
    const contar = estado => citas.filter(cita => cita.estado === estado).length;
    // Solo las citas atendidas generan ingresos; pendientes y canceladas no se cobran.
    const centavos = citas.filter(cita => cita.estado === 'ATENDIDA')
      .reduce((suma, cita) => suma + Math.round(Number(cita.total) * 100), 0);
    res.json({ totalCitas: citas.length, pendientes: contar('PENDIENTE'),
      atendidas: contar('ATENDIDA'), canceladas: contar('CANCELADA'), ingresoTotal: centavos / 100 });
  } catch (error) {
    res.status(502).json({ error: 'No fue posible consultar la API de Spring Boot' });
  }
});
app.listen(process.env.NODE_PORT || 3000, () => console.log('Servicio Node.js: http://localhost:' + (process.env.NODE_PORT || 3000) + '/resumen-citas'));
