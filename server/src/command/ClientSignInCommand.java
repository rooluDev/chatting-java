package command;

import dto.Client;
import repository.ClientRepository;

/**
 * CLIENT_SIGN_IN Command
 */
public class ClientSignInCommand implements Command<String> {

    private final Client client;
    private final ClientRepository clientRepository = ClientRepository.getInstance();

    public ClientSignInCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute(String clientName) {
        client.setName(clientName);
        clientRepository.add(client);
    }
}
