# Variabel
JAVAC = javac
JAVA = java
SRC_DIR = src
BIN_DIR = bin
MAIN_CLASS = icesolver.Main

# Command Compile
all : compile

# Target Compile
compile : 
	@echo "Compiling program..."
	mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) \
	$(SRC_DIR)/icesolver/*.java \
	$(SRC_DIR)/icesolver/heuristic/*.java \
	$(SRC_DIR)/icesolver/model/*.java \
	$(SRC_DIR)/icesolver/movement/*.java \
	$(SRC_DIR)/icesolver/output/*.java \
	$(SRC_DIR)/icesolver/parser/*.java \
	$(SRC_DIR)/icesolver/solver/*.java

# Target Running Program
run : 
	@echo "Running program..."
	$(JAVA) -cp $(BIN_DIR) $(MAIN_CLASS)

# Target Cleaning File Class
clean:
	@echo "Cleaning file bin..."
	rm -rf $(BIN_DIR)