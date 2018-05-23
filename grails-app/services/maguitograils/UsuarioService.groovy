package maguitograils

import grails.gorm.transactions.Transactional
import myapp.Role
import myapp.User
import myapp.UserRole

@Transactional
class UsuarioService {

    def registrarUsuario(UserRegisterForm userRegisterForm) {
        def newuser = new User(username: userRegisterForm.userName, password: userRegisterForm.password).save()
        Role role

        if (userRegisterForm.rol.equals("Aventurero")){
            role = Role.findByAuthority('ROLE_AVENTURERO_USER')
        }
        else{
            role = Role.findByAuthority('ROLE_DUNGEONERO_USER')
        }

        UserRole.create(newuser, role,true)
    }

    def savePersonaje(Personaje unPersonaje, def unUsuario) {
        unPersonaje.setDuenioID(unUsuario.id)
        unPersonaje.save(flush:true)

        unPersonaje
    }
}
