package command;

import dto.Client;
import repository.ClientRepository;

/**
 * CLIENT_SIGN_OUT Command
 */
public class ClientSignOutCommand implements Command {

    private final ClientRepository clientRepository = ClientRepository.getInstance();
    private final Client client;

    public ClientSignOutCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute(Object data) {
        clientRepository.delete(client);
    }
}
