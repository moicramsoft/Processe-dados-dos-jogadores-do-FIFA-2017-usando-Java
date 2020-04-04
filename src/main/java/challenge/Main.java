package challenge;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
	// Faz a leitura do arquivo //
	String csvReader = "data.csv";
	String splitColumn = ",";
	List<String[]> CSVdata = ReadCsv(csvReader, splitColumn);
	// pega a informação de um array list//
	private List<String> columns = Arrays.asList(CSVdata.get(0));
	// getting the sublist //
	List<String[]> players = CSVdata.subList(1, CSVdata.size());

	private static List<String[]> ReadCsv(String csvReader, String splitColumn) {

		List<String[]> data = new ArrayList<>();
		File file = null;
		Reader reader = null;
		BufferedReader bufferedReader = null;

		try {

			file = new File(Main.class.getClassLoader().getResource(csvReader).getFile());

			reader = new FileReader(file);

			bufferedReader = new BufferedReader(reader);

			// read and put data as arraylist//

			bufferedReader.lines().forEach(s -> data.add(s.split(splitColumn)));

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				bufferedReader.close();

				reader.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return data;

	}

	// Quantas nacionalidades (coluna `nationality`) diferentes existem no arquivo?
	public int q1() {

		int nationalityPlayers;
		nationalityPlayers = players.stream().collect(Collectors.groupingBy(x -> x[columns.indexOf("nationality")]))
				.size();
		return nationalityPlayers;

	}

	// Quantos clubes (coluna `club`) diferentes existem no arquivo?
	// Obs: Existem jogadores sem clube.
	public int q2() {
		// filter -> players without club, groupBy -> how much different clubs //
		int club;
		club = players.stream().filter(x -> !x[columns.indexOf("club")].isEmpty())
				.collect(Collectors.groupingBy(x -> x[columns.indexOf("club")])).size();
		return club;

	}

	// Liste o nome completo (coluna `full_name`) dos 20 primeiros jogadores.
	public List<String> q3() {

		List<String> fullName;
		fullName = players.stream().map(x -> x[columns.indexOf("full_name")]).limit(20).collect(Collectors.toList());
		return fullName;
	}

	// Quem são os top 10 jogadores que possuem as maiores cláusulas de rescisão?
	// (utilize as colunas `full_name` e `eur_release_clause`)
	public List<String> q4() {
		List<String> top10;
		// sorted -> Do a crescent toplist //
		top10 = players.stream().filter(j -> !j[columns.indexOf("eur_release_clause")].isEmpty())
				.sorted(Comparator.comparing(j -> Double.parseDouble(j[columns.indexOf("eur_release_clause")])))
				.map(x -> x[columns.indexOf("full_name")]).collect(Collectors.toList());
		Collections.reverse(top10);

		return top10.stream().limit(10).collect(Collectors.toList());
	}

	// Quem são os 10 jogadores mais velhos (use como critério de desempate o campo
	// `eur_wage`)?
	// (utilize as colunas `full_name` e `birth_date`)
	public List<String> q5() {

		List<String> top10old;

		top10old = players.stream().filter(j -> !j[columns.indexOf("birth_date")].isEmpty())
				.sorted(Comparator.comparing(x -> x[columns.indexOf("birth_date")]))
				.map(x -> x[columns.indexOf("full_name")]).collect(Collectors.toList());

		return top10old.stream().limit(10).collect(Collectors.toList());
	}

	// Conte quantos jogadores existem por idade. Para isso, construa um mapa onde
	// as chaves são as idades e os valores a contagem.
	// (utilize a coluna `age`)
	public Map<Integer, Integer> q6() {

		return players.stream().collect(Collectors.groupingBy(j -> Integer.parseInt(j[columns.indexOf("age")]),
				Collectors.reducing(0, e -> 1, Integer::sum)));
	}
}