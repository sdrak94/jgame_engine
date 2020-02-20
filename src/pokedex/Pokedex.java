package pokedex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import engine.template.PokemonTemplate;

public class Pokedex
{
	private final ArrayList<PokemonTemplate> _pokedex = new ArrayList<>();
	private final PokemonTemplate UNKN = new PokemonTemplate(0, "MISSINGNO.");

	public Pokedex()
	{	try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("pokedex.txt"))))
		{	String str;
			while ((str = br.readLine()) != null)
			{	final String[] arr = str.split(" : ");
				addPokemon(Integer.parseInt(arr[0]), arr[1]);
			}
		}
		catch (Exception e)
		{	e.printStackTrace();
		}
	}
	
	public void addPokemon(int id, String name)
	{
		_pokedex.add(new PokemonTemplate(id, name));
	}
	
	public PokemonTemplate getPokemonByID(int id)
	{
		return getPokemon((pt) -> pt.getId() == id);
	}
	
	public PokemonTemplate getPokemon(Dexter search)
	{
		for (PokemonTemplate pt : _pokedex)
			if (search.isSearchValid(pt))
				return pt;
		return UNKN;
	}
	
	public interface Dexter
	{
		public boolean isSearchValid(PokemonTemplate pt);
	}
	
	private static class SingletonHolder
	{
		private static Pokedex _instance = new Pokedex();
	}
	
	public static Pokedex getInstance()
	{
		return SingletonHolder._instance;
	}
}
