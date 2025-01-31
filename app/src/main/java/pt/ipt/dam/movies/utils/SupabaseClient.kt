package pt.ipt.dam.movies.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

/**
 * Objeto que fornece a conexão com o Supabase
 * Fornece acesso ao cliente Supabase em toda a aplicação
 */
object SupabaseClient {
    private const val SUPABASE_URL = "https://axfubqovxdwtthooxixf.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImF4ZnVicW92eGR3dHRob294aXhmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzgwNzQxMjcsImV4cCI6MjA1MzY1MDEyN30.Q48OtoTXMIZY4b16BRhm-3zUzzxNnJnSTH8d30xiZAk"

    /**
     * Cliente Supabase configurado com as credenciais da aplicação
     * Utilizado para autenticação e acesso à base de dados
     */
    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        /**
         * Módulo para autenticação de utilizadores
         * Permite operações como login, registo e controlo de sessão
         */
        install(GoTrue)
        /**
         * Módulo para acesso à base de dados PostgreSQL
         * Permite operações CRUD nas tabelas da base de dados
         */
        install(Postgrest)
    }
}
