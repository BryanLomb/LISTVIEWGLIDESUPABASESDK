# LISTVIEWGLIDESUPABASESDK
Aplicación Android que muestra una lista de alumnos desde una base de datos Supabase, con imágenes cargadas mediante Glide. Permite filtrar por semestre académico y materia usando menús desplegables interactivos.

## Características

- **Filtrado por semestre y materia** – Selecciona un semestre y luego una materia de la lista cargada dinámicamente.
- **Lista de alumnos** – Muestra nombres, correos, teléfonos y fotos de perfil (recorte circular).
- **Carga de imágenes** – Usa Glide para carga eficiente, caché y placeholders/errores.
- **Integración con Supabase** – Obtiene datos en tiempo real de las tablas `alumnos` y `materias`.
- **Manejo de errores** – Diálogos amigables para excepciones REST de Supabase.

## Capturas de pantalla

*(Agrega tus propias capturas aquí)*

| Pantalla principal | Lista de alumnos |
|-------------------|------------------|
| <img width="654" height="1459" alt="image" src="https://github.com/user-attachments/assets/3d577854-ac1f-41a6-8107-4947238985ba" />|

## Tecnologías utilizadas

- **Lenguaje:** Kotlin
- **UI:** Material Design 3, ConstraintLayout, ListView, AutoCompleteTextView
- **Red y base de datos:** Supabase (PostgREST), Ktor Client Android
- **Carga de imágenes:** Glide
- **Serialización:** kotlinx.serialization.json
- **Arquitectura:** MVVM con coroutines (`lifecycleScope`)

## Requisitos previos

- Android Studio (última versión recomendada)
- JDK 11 o superior
- Un proyecto de Supabase con las siguientes tablas:

### Tablas de Supabase

#### `alumnos`
| Columna    | Tipo   | Descripción                         |
|------------|--------|-------------------------------------|
| id         | int8   | Llave primaria                      |
| nombres    | text   | Nombre completo del alumno          |
| correo     | text   | Correo electrónico                  |
| telefono   | text   | Número de teléfono                  |
| paralelo   | text   | Grupo / paralelo                    |
| foto       | text   | Ruta relativa de la foto de perfil  |

> **Nota:** Las fotos se cargan desde `https://sga.uteq.edu.ec` + valor de `foto`.

#### `materias`
| Columna  | Tipo   | Descripción                     |
|----------|--------|---------------------------------|
| id       | int8   | Llave primaria                  |
| nombre   | text   | Nombre de la materia            |
| nivel    | int2   | Número del semestre (1–6)       |
