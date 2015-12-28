package  ledg.Repository


import ledg.Entity.WeblogEntry
import org.springframework.data.repository.CrudRepository

interface WeblogRepository : CrudRepository<WeblogEntry, Int>

