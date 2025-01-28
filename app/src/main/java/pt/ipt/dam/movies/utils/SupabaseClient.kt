package pt.ipt.dam.movies.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    private const val SUPABASE_URL = "https://axfubqovxdwtthooxixf.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImF4ZnVicW92eGR3dHRob294aXhmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzgwNzQxMjcsImV4cCI6MjA1MzY1MDEyN30.Q48OtoTXMIZY4b16BRhm-3zUzzxNnJnSTH8d30xiZAk"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(GoTrue)
        install(Postgrest)
    }
}
