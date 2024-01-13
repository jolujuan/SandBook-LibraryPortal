package org.sandbook.srv;

import java.util.ArrayList;
import java.util.List;

import org.sandbook.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	private static List<Usuario> listaUsuario=new ArrayList<Usuario>();
	
	static {
		listaUsuario.add(new Usuario("joselu","miPassword@1"));
		listaUsuario.add(new Usuario("joseramon","miPassword@1"));

	}
	
	public boolean usuarioValidado(Usuario usuarioLogin) {
		Usuario usuarioEncontrado= encontrarUsuarioPorNickname(usuarioLogin.getNickname());
	
		if (usuarioEncontrado!=null&&usuarioEncontrado.getPassword().equals(usuarioLogin.getPassword())) {
			return true;
		}return false;
	}
	
	public Usuario encontrarUsuarioPorNickname(String nickname) {
		Usuario usuarioEncontrado=null;
		for (Usuario usuario : listaUsuario) {
			if (usuario.getNickname().equals(nickname)) {
				usuarioEncontrado=usuario;
			}
		}
		return usuarioEncontrado;
	}

	public void modificaUsuario(Usuario usuarioAModificar, String nickname) {
		// TODO Auto-generated method stub
		
	}
}
