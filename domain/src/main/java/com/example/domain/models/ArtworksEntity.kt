package com.example.domain.models

data class ArtworksEntity(
    val config: Config,
    val data: List<Data>,
    val info: Info,
    val pagination: Pagination
)

data class Color(
    val h: Int,
    val l: Int,
    val percentage: Double,
    val population: Int,
    val s: Int
)

data class Config(
    val iiif_url: String,
    val website_url: String
)

data class Contexts(
    val groupings: List<String>
)

data class Data(
    val alt_artist_ids: List<Any>,
    val alt_classification_ids: List<String>,
    val alt_image_ids: List<Any>,
    val alt_material_ids: List<String>,
    val alt_style_ids: List<Any>,
    val alt_subject_ids: List<String>,
    val alt_technique_ids: List<String>,
    val alt_titles: Any,
    val api_link: String,
    val api_model: String,
    val artist_display: String,
    val artist_id: Int,
    val artist_ids: List<Int>,
    val artist_title: String,
    val artist_titles: List<String>,
    val artwork_type_id: Int,
    val artwork_type_title: String,
    val boost_rank: Any,
    val catalogue_display: Any,
    val category_ids: List<String>,
    val category_titles: List<String>,
    val classification_id: String,
    val classification_ids: List<String>,
    val classification_title: String,
    val classification_titles: List<String>,
    val color: Color,
    val colorfulness: Double,
    val copyright_notice: String,
    val credit_line: String,
    val date_display: String,
    val date_end: Int,
    val date_qualifier_id: Int,
    val date_qualifier_title: String,
    val date_start: Int,
    val department_id: String,
    val department_title: String,
    val description: String,
    val dimensions: String,
    val dimensions_detail: List<DimensionsDetail>,
    val document_ids: List<Any>,
    val edition: Any,
    val exhibition_history: String,
    val fiscal_year: Int,
    val fiscal_year_deaccession: Any,
    val gallery_id: Int,
    val gallery_title: String,
    val has_advanced_imaging: Boolean,
    val has_educational_resources: Boolean,
    val has_multimedia_resources: Boolean,
    val has_not_been_viewed_much: Boolean,
    val id: Int,
    val image_id: String,
    val inscriptions: String,
    val internal_department_id: Int,
    val is_boosted: Boolean,
    val is_on_view: Boolean,
    val is_public_domain: Boolean,
    val is_zoomable: Boolean,
    val latitude: Any,
    val latlon: Any,
    val longitude: Any,
    val main_reference_number: String,
    val material_id: String,
    val material_ids: List<String>,
    val material_titles: List<String>,
    val max_zoom_window_size: Int,
    val medium_display: String,
    val nomisma_id: Any,
    val on_loan_display: Any,
    val place_of_origin: String,
    val provenance_text: String,
    val publication_history: String,
    val publishing_verification_level: String,
    val section_ids: List<Any>,
    val section_titles: List<Any>,
    val short_description: Any,
    val site_ids: List<Any>,
    val sound_ids: List<Any>,
    val source_updated_at: String,
    val style_id: String,
    val style_ids: List<String>,
    val style_title: String,
    val style_titles: List<String>,
    val subject_id: String,
    val subject_ids: List<String>,
    val subject_titles: List<String>,
    val suggest_autocomplete_all: List<SuggestAutocompleteAll>,
    val technique_id: String,
    val technique_ids: List<String>,
    val technique_titles: List<String>,
    val term_titles: List<String>,
    val text_ids: List<Any>,
    val theme_titles: List<Any>,
    val thumbnail: Thumbnail?,
    val timestamp: String,
    val title: String,
    val updated_at: String,
    val video_ids: List<Any>
)

data class DimensionsDetail(
    val clarification: String,
    val depth: Int,
    val diameter: Any,
    val height: Int,
    val width: Int
)

data class Info(
    val license_links: List<String>,
    val license_text: String,
    val version: String
)

data class Pagination(
    val current_page: Int,
    val limit: Int,
    val next_url: String,
    val offset: Int,
    val total: Int,
    val total_pages: Int
)

data class SuggestAutocompleteAll(
    val contexts: Contexts,
    val input: List<String>,
    val weight: Int
)

data class Thumbnail(
    val alt_text: String,
    val height: Int,
    val lqip: String,
    val width: Int
)