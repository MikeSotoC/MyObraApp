# MyObra - Ideas de Funcionalidades Futuras (Android)

## 🎯 Enfoque: Aplicación Móvil Ligera y Práctica

Esta hoja de ruta está enfocada exclusivamente en funcionalidades realistas para dispositivos Android, priorizando rendimiento, usabilidad en campo y bajo consumo de recursos.

---

## 📱 Funcionalidades Principales para Agregar

### 1. **Módulo de Topografía Esencial** ✅ COMPLETO
- [x] **Nivelación Simple**: Cálculo rápido de cotas con compensación básica
- [x] **Nivelación Compuesta**: Libreta de campo simplificada con cálculos automáticos
- [x] **Poligonal Rápida**: Cierre y compensación simplificada (método brújula) para levantamientos rápidos
- [x] **Radiación Básica**: Registro de puntos desde estación total
- [ ] **Perfil Longitudinal**: Visualización gráfica ligera del terreno y rasante (en progreso)
- [ ] **Exportación CSV/PDF**: Compartir informes fácilmente por WhatsApp, Email (parcialmente implementado)

### 2. **Módulo de Materiales (Zanjas)** ✅ COMPLETO
- [x] **Cálculo de Corte/Relleno**: Estimación rápida para zanjas de tuberías
- [x] **Materiales por Tramo**: Arena, concreto, ladrillo según tipo de zanja
- [ ] **Resumen de Cantidades**: Totalización automática por proyecto

### 3. **Mejoras de UX/UI Móvil**
- [ ] **Modo offline completo**: Sin dependencia de conexión a internet
- [ ] **Historial local**: Acceso rápido a proyectos anteriores almacenados en el dispositivo
- [ ] **Plantillas predefinidas**: Configuraciones guardadas para proyectos recurrentes
- [ ] **Gráficos ligeros**: Visualización de perfiles optimizada para móviles
- [ ] **Reportes PDF simples**: Generación de informes básicos con membrete
- [x] **Compartir resultados**: Exportar a CSV, WhatsApp, Email directamente
- [x] **Tema claro/oscuro**: Selector manual o automático según sistema
- [ ] **Inputs optimizados**: Teclado numérico personalizado para datos de topografía

### 4. **Integración con Hardware Móvil**
- [ ] **GPS básico**: Capturar coordenadas simples del dispositivo (precisión estándar)
- [ ] **Fotos adjuntas**: Adjuntar fotos del galería a los puntos de control
- [x] **Portapapeles inteligente**: Copiar/pegar coordenadas desde otras apps
- [x] **Permisos de almacenamiento**: Gestión simple para guardar/exportar archivos

### 5. **Utilidades Esenciales**
- [x] **Conversor de unidades**: Unidades comunes de construcción (m, cm, mm, ft, in)
- [ ] **Calculadora básica**: Operaciones rápidas integradas
- [ ] **Tablas de referencia rápida**: Propiedades básicas de materiales comunes

---

## 📊 Priorización Sugerida para Android

### Fase 1 (Corto Plazo - 2-4 semanas) ✅ COMPLETADO
- [x] Mejorar gráfico de perfil longitudinal actual
- [ ] Agregar exportación a PDF básico
- [ ] Historial de proyectos locales (Room Database simple)
- [x] Optimizar inputs para teclado numérico

### Fase 2 (Mediano Plazo - 2-3 meses) - EN PROGRESO
- [ ] Nivelación compuesta completa
- [x] Poligonal rápida con compensación (lógica implementada, falta UI)
- [ ] Gráfico interactivo de perfil (zoom, pan)
- [ ] Plantillas de proyectos recurrentes

### Fase 3 (Largo Plazo - 4-6 meses)
- [ ] Módulo de estructuras básicas (vigas simples)
- [ ] Integración GPS mejorada
- [ ] Fotos georreferenciadas básicas
- [ ] Sincronización opcional con nube (Drive/Dropbox)

---

## 💡 Recomendaciones Técnicas para Android

### Arquitectura ✅ IMPLEMENTADA
- [x] Mantener arquitectura MVVM limpia
- [ ] Usar Room Database para almacenamiento local ligero
- [x] Implementar inyección de dependencias (Hilt)
- [x] Evitar librerías pesadas innecesarias

### Performance Móvil
- [x] LazyColumn para listas grandes
- [ ] Optimizar uso de memoria en gráficos
- [ ] Caché inteligente de cálculos recientes
- [x] Minimizar permisos solicitados

### UX en Campo
- [ ] Botones grandes para uso con guantes
- [ ] Contraste alto para visibilidad exterior
- [ ] Guardado automático cada 30 segundos
- [ ] Feedback háptico en acciones importantes
- [ ] Modo "solo lectura" para revisión rápida

### Limitaciones a Considerar
- ❌ NO incluir: Curvas de nivel complejas (muy pesado para móvil)
- ❌ NO incluir: Volumetría avanzada de prismas (usar áreas promedio simple)
- ❌ NO incluir: Exportación nativa a DXF (mejor usar servicios externos o simplificar)
- ❌ NO incluir: Intersecciones avanzadas (Cassini, Pothenot - muy específico)
- ❌ NO incluir: BIM, AR, drones (sobrecarga la app)
- ❌ NO incluir: Trabajo colaborativo en tiempo real (complejo para MVP)
- ❌ NO incluir: Módulos completos de vías, hidráulica, presupuestos (enfocarse en lo esencial)

---

## 🎨 Mejoras de Diseño Específicas para Móvil

1. [ ] **Onboarding rápido**: Tutorial de 3 pantallas máximo
2. [ ] **Animaciones sutiles**: Feedback visual sin consumir batería
3. [ ] **Skeleton loading**: Mientras cargan datos locales
4. [x] **Dark mode completo**: Ahorro de batería en OLED
5. [ ] **Haptic feedback**: Vibración corta en cálculos completados
6. [ ] **Gestos básicos**: Deslizar para borrar, doble tap para editar
7. [ ] **Búsqueda local**: Filtrar proyectos guardados
8. [ ] **Widget simple**: Acceso directo al último proyecto

---

## 🚫 Lo que NO se implementará (Enfoque Minimalista)

Para mantener la app ligera y útil en campo, se excluyen:

- Curvas de nivel generadas automáticamente
- Volumetría compleja por prismas
- Poligonales con múltiples métodos de compensación
- Intersecciones directas/inversas avanzadas
- Diseño estructural completo (vigas, columnas, zapatas)
- Redes hidráulicas y sanitarias
- Alineamiento de carreteras completo
- Realidad aumentada
- Integración con drones
- BIM/IFC
- Trabajo colaborativo multiusuario
- Cronogramas Gantt/PERT
- Presupuestos completos con APU
- Normativas locales extensas

**Filosofía**: Una herramienta simple, rápida y confiable que resuelva el 80% de las necesidades diarias en campo, sin sobrecargar al usuario ni al dispositivo.

---

## 📦 Estado Actual del Proyecto

### Archivos Implementados (35 archivos Kotlin)

#### Dominio (Lógica de Negocio)
- `CalcularTopografia.kt` - Perfil longitudinal y nivelación
- `CalcularPoligonal.kt` - Poligonal cerrada/abierta con compensación Bowditch
- `CalcularRadiacion.kt` - Cálculo de puntos por radiación
- `CalcularMaterialesZanja.kt` - Cálculo de materiales para zanjas

#### Modelos de Datos
- `PuntoPerfil.kt`, `PuntoRasante.kt` - Perfil longitudinal
- `PuntoPoligonal.kt`, `ResultadoPoligonal.kt` - Poligonales
- `PuntoRadiacion.kt`, `ResultadoRadiacion.kt` - Radiación
- `ResultadoMateriales.kt`, `MaterialTramo.kt` - Materiales

#### UI (Jetpack Compose)
- `TopNivScreen.kt` - Pantalla principal de nivelación
- Componentes modulares: ErrorCard, ResultadosCard, MaterialesExpandableSection
- ViewModels con Hilt: TopNivViewModel, PoligonalViewModel, RadiacionViewModel

#### Navegación
- `Screen.kt` - Rutas: Home, Nivelacion, Poligonal, Radiacion
- `AppNavGraph.kt` - Grafo de navegación configurado

#### Utilidades
- Exportación CSV, conversión de unidades, manejo de portapapeles
- Filtros de input, utilidades de diálogo y snackbar

---

*Documento generado para MyObra - Aplicación Android de Ingeniería Civil*
*Última actualización: 2024*
*Enfoque: Minimalista, práctico, optimizado para móviles*
