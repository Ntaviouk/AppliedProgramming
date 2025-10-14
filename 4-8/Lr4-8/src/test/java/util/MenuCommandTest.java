package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuCommandTest {

    // Створюємо просту конкретну реалізацію абстрактного класу для тестування
    private static class ConcreteMenuCommand extends MenuCommand {
        public ConcreteMenuCommand(String name) {
            super(name);
        }

        @Override
        public void execute() {
            // В цьому тесті реалізація не важлива
        }
    }

    @Test
    void getName_shouldReturnConstructorName() {
        // Arrange
        String expectedName = "Test Command";
        MenuCommand command = new ConcreteMenuCommand(expectedName);

        // Act
        String actualName = command.getName();

        // Assert
        assertEquals(expectedName, actualName, "The name should be the one provided in the constructor.");
    }
}