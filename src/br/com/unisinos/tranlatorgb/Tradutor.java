package br.com.unisinos.tranlatorgb;

import br.com.unisinos.tranlatorgb.arvore.ArvoreAVL;
import br.com.unisinos.tranlatorgb.arvore.Nodo;
import br.com.unisinos.tranlatorgb.exceptions.NodoInvalidoException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tradutor {

    protected void carregaDicionario(String arq) {
        ArvoreAVL arvoreAVL = null;
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arq))) {

            Dicionario dicionario = null;
            String linha;
            List<Dicionario> palavrasDicionario = new ArrayList<>();

            while ((linha = br.readLine()) != null) {

                List<String> traducoes = new ArrayList<>();
                String[] palavras = linha.split("#");
                String chave = palavras[0];

                for (int i = 1; i < palavras.length; i++) {
                    traducoes.add(palavras[i]);
                }

                dicionario = new Dicionario(chave, traducoes);
                palavrasDicionario.add(dicionario);

                Nodo novoNodo = new Nodo(dicionario);
                if (count == 0) {
                    arvoreAVL = new ArvoreAVL(novoNodo);
                    count++;
                } else {
                    try {
                        count++;
                        arvoreAVL.insereNodo(novoNodo, arvoreAVL.getRaiz());
                    } catch (NodoInvalidoException nie) {
                        System.out.println(nie.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> traduzPalavra(String palavra) {
        return Collections.emptyList();
    }

    public void insereTraducao(String palavra, List<String> definicoes) {

        StringBuilder builder = new StringBuilder();

        builder.append(palavra)
                .append("#");

        for (int i = 0; i < definicoes.size(); i++) {
            if (i == definicoes.size() - 1) {
                builder.append(definicoes.get(i));
            } else {
                builder.append(definicoes.get(i))
                        .append("#");
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("dicionario.dat", true));

            if (new File("dicionario.dat").length() != 0) {
                writer.newLine();
            }
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void salvaDicionario(String arq) {
    }

}
