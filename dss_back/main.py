import asyncio

from core import (
    Reader,
    Writer,
    calculate_binary_relations,
    calculate_summary
)
from core.mechanisms import (
    calculate_k_max_options,
    calculate_k_max_results_by_variant,
    calculate_dominance,
    calculate_dominance_results_by_variant,
    calculate_lock,
    calculate_lock_results_by_variant,
    calculate_tournament,
    calculate_tournament_results_by_variant
)


# async def runner():
def runner():
    files_reader = Reader()

    # MAIN TEST DATA
    df = files_reader(
        matrix_row_names_path="./input_data/test_data/preference_names_for_matrix_rows.csv",
        matrix_path="./input_data/test_data/matrix.csv",
        choice_function_path="./input_data/test_data/choice_function.csv",
        weight_coefficients_path="./input_data/test_data/weight_coefficients.csv"
    )



    df_binary = calculate_binary_relations(df)

    # df_k_max = await calculate_k_max_options(df_binary)
    # df_dominance = await calculate_dominance(df_binary)
    # df_lock = await calculate_lock(df_binary)
    # df_tournament = await calculate_tournament(df_binary)
    #
    # df_k_max_by_variant = await calculate_k_max_results_by_variant(df_k_max)
    # df_dominance_by_variant = await calculate_dominance_results_by_variant(df_dominance)
    # df_lock_by_variant = await calculate_lock_results_by_variant(df_lock)
    # df_tournament_by_variant = await calculate_tournament_results_by_variant(df_tournament)
    #
    df_k_max = calculate_k_max_options(df_binary)
    df_dominance = calculate_dominance(df_binary)
    df_lock = calculate_lock(df_binary)
    df_tournament = calculate_tournament(df_binary)

    df_k_max_by_variant = calculate_k_max_results_by_variant(df_k_max)
    df_dominance_by_variant = calculate_dominance_results_by_variant(df_dominance)
    df_lock_by_variant = calculate_lock_results_by_variant(df_lock)
    df_tournament_by_variant = calculate_tournament_results_by_variant(df_tournament)

    df_summary = calculate_summary(
        df_dominance_by_variant,
        df_lock_by_variant,
        df_tournament_by_variant,
        df_k_max_by_variant
    )

    writer = Writer()
    writer(
        dir_to_save="./decision_support_system/output_data",
        raw_df=df,
        binary_rel_df=df_binary,
        dominance_df=df_dominance,
        dominance_df_by_variant=df_dominance_by_variant,
        lock_df=df_lock,
        lock_df_by_variant=df_lock_by_variant,
        tournament_df=df_tournament,
        tournament_df_by_variant=df_tournament_by_variant,
        k_max_df=df_k_max,
        k_max_df_by_variant=df_k_max_by_variant,
        summary_df=df_summary
    )


def main():
    # asyncio.run(runner())
    runner()

if __name__ == '__main__':
    main()

# from typing import Union
# from models.raw_data import RawDataframe, RawDataframeModel
# from fastapi import FastAPI
#
# app = FastAPI()
#
#
# @app.get("/")
# def read_root():
#     return {"info": "This is DSS backend made with FastAPI"}
#
#
# @app.get("/help")
# def get_routes():
#     return {
#         "calculate_tournament": "POST /mechanism/tournament",
#         "calculate_kmax": "POST /mechanism/kmax",
#         "calculate_kmax_optimal": "POST /mechanism/kmax_optimal",
#         "calculate_locking": "POST /mechanism/locking",
#         "calculate_dominance": "POST /mechanism/dominance"
#     }
#
#
# @app.post("/mechanism/tournament")
# def calculate_tournament_f(*, raw_data: RawDataframeModel):
#     df_binary = calculate_binary_relations(raw_data)
#     df_tournament = calculate_tournament(df_binary)
#     return {"Hello": "World"}
#
#
# @app.post("/mechanism/kmax")
# def calculate_kmax(*, raw_data: RawDataframeModel):
#     df_binary = calculate_binary_relations(raw_data)
#     df_kmax = calculate_k_max_options(df_binary)
#     return {"Hello": "World"}
#
#
# @app.post("/mechanism/kmax_optimal")
# def calculate_kmax_optimal(*, raw_data: RawDataframeModel):
#     df_binary = calculate_binary_relations(raw_data)
#     df_tournament = calculate_tournament(df_binary)
#     return {"Hello": "World"}
#
#
# @app.post("/mechanism/locking")
# def calculate_locking(*, raw_data: RawDataframeModel):
#     df_binary = calculate_binary_relations(raw_data)
#     df_lock = calculate_lock(df_binary)
#     return {"Hello": "World"}
#
#
# @app.get("/mechanism/dominance")
# def calculate_dominance_f(*, raw_data: RawDataframeModel):
#     df_binary = calculate_binary_relations(raw_data)
#     df_tournament = calculate_dominance(df_binary)
#     return {"Hello": "World"}