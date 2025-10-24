
Puerto: `http://localhost:8080`

---

Usuarios de Prueba

Se crean automáticamente al iniciar:

ADMIN 
- Email: `admin@food.com`
- Password: `admin123`

CLIENTE
- Email: `juan@mail.com`
- Password: `123456`

---

Endpoints

Registrar Usuario
```
POST /api/usuarios/register

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@mail.com",
  "password": "123456",
  "rol": "USUARIO"
}
```

Login
```
POST /api/usuarios/login

{
  "email": "juan@mail.com",
  "password": "123456"
}
```

 uscar todos los usuarios
```
GET /api/usuarios
```

Buscar usuario por ID
```
GET /api/usuarios/{id}
```

Actualizar usuario
```
PUT /api/usuarios/{id}

{
  "nombre": "Nuevo Nombre",
  "apellido": "Nuevo Apellido"
}
```

### Eliminar usuario
```
DELETE /api/usuarios/{id}
```

---

## 🗄️ Base de Datos

- PostgreSQL en `localhost:5432`
- Base de datos: `back_food_store`
- Usuario: `postgres`
- Password: `La que tengan en su postgres`
