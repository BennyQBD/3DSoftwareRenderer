#Copyright (c) 2014, Benny Bobaganoosh
#All rights reserved.
#
#Redistribution and use in source and binary forms, with or without
#modification, are permitted provided that the following conditions are met:
#
#1. Redistributions of source code must retain the above copyright notice, this
#   list of conditions and the following disclaimer. 
#2. Redistributions in binary form must reproduce the above copyright notice,
#  this list of conditions and the following disclaimer in the documentation
#   and/or other materials provided with the distribution.
#
#THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
#ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
#WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
#DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
#ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
#(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
#LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
#ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
#(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

OUTPUT_NAME = main

SRC_DIR := .
OBJ_DIR := ../obj
OUTPUT_DIR := ../

CC := clang
CXX := clang++
JAVA := javac
ASM := nasm

LD_FLAGS := -O3
C_FLAGS := -O3
CXX_FLAGS := -O3 -Weverything
ASM_FLAGS := -f elf64

JAVA_FILES := $(wildcard $(SRC_DIR)/*.java)
JAVAOBJ_FILES := $(addprefix $(OBJ_DIR)/,$(notdir $(JAVA_FILES:.java=.class)))

CPP_FILES := $(wildcard $(SRC_DIR)/*.cpp)
CPPOBJ_FILES := $(addprefix $(OBJ_DIR)/,$(notdir $(CPP_FILES:.cpp=.o)))

C_FILES := $(wildcard $(SRC_DIR)/*.c)
COBJ_FILES := $(addprefix $(OBJ_DIR)/,$(notdir $(C_FILES:.c=.o)))

ASM_FILES := $(wildcard $(SRC_DIR)/*.asm)
ASMOBJ_FILES := $(addprefix $(OBJ_DIR)/,$(notdir $(ASM_FILES:.asm=.o)))

#-S -mllvm --x86-asm-syntax=intel
main: $(JAVAOBJ_FILES) 
	
	
$(OBJ_DIR)/%.o: $(SRC_DIR)/%.c
	$(CC) $(C_FLAGS) -c $< -o $@
	
$(OBJ_DIR)/%.o: $(SRC_DIR)/%.cpp
	$(CXX) $(CXX_FLAGS) -c $< -o $@

$(OBJ_DIR)/%.o: $(SRC_DIR)/%.asm
	$(ASM) $(ASM_FLAGS) $< -o $@

$(OBJ_DIR)/%.class: $(SRC_DIR)/%.java
	$(JAVA) $< -d $(OBJ_DIR)


clean:
	rm -f $(OBJ_DIR)/*
	rm -f $(OUTPUT_DIR)/$(OUTPUT_NAME)
	
